package com.fawez.ecommerce.product;

import com.fawez.ecommerce.exception.ProductPurchaseException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;
    public Integer createProduct(ProductRequest request) {
        Product save=repository.save(mapper.toProduct(request));
        return save.getId();

    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds= request
                .stream().map(ProductPurchaseRequest::productId)
                .collect(Collectors.toList());
        var storedProducts=repository.findByIdInOrderById(productIds);
        if(productIds.size()!=storedProducts.size()){

            throw new ProductPurchaseException("one or more products does not exist");

        }
        var storedRequest=request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchaseProduct = new ArrayList<ProductPurchaseResponse>();
        for(int i =0;i<storedProducts.size();i++){
            var product=storedProducts.get(i);
            var productRequest=storedRequest.get(i);
            if(product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("there is no stock with id"+ product.getId());
            }
            var newAvailableQuantity=product.getAvailableQuantity()-productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchaseProduct.add(mapper.toproductPurchaseResponse(product,productRequest.quantity()));
        }
        return purchaseProduct;

    }

    public ProductResponse findById(Integer productId) {
        Product findProduct= repository.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException("product not found with the id"));
        return mapper.toProductResponse(findProduct);

    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }
}
