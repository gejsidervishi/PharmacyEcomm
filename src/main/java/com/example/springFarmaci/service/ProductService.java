package com.example.springFarmaci.service;

import com.example.springFarmaci.dto.CartItemDTO;
import com.example.springFarmaci.dto.ProductDTO;
import com.example.springFarmaci.dto.mappers.DTOMappers;
import com.example.springFarmaci.models.Cart_Items;
import com.example.springFarmaci.models.Product;
import com.example.springFarmaci.models.User;
import com.example.springFarmaci.repository.CartItemsRepository;
import com.example.springFarmaci.repository.ProductRepository;
import com.example.springFarmaci.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Autowired
    private DTOMappers dtoMappers;

    @Autowired
    private MyUserDetailService myUserDetailService;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Cart_Items addToCart(Long productId) {
        Cart_Items existingCartItem = cartItemsRepository.isPresent(myUserDetailService.getCurrentUser().getId(), productId);
        if(existingCartItem != null) {
            if(existingCartItem.getQuantity() < 5) {
                incrementQuantity(existingCartItem.getId());
                return existingCartItem;
            } else {
                return null;
            }
        } else {
            User user = myUserDetailService.getCurrentUser();
            Cart_Items cart_item = new Cart_Items();
            cart_item.setUser(user);
            Product product = productRepository.getOne(productId);
            cart_item.setProduct(product);
            cart_item.setQuantity(1);
            return cartItemsRepository.save(cart_item);
        }
    }

    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()) {
            product.get().setToDate(new Date());
            productRepository.delete(product.get());
        }

    }

    public Product addProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice((int) productDTO.getPrice());
        product.setImgPath(productDTO.getImgPath());
        product.setQuantity(productDTO.getQuantity());
        product.setToDate(Date.from(Instant.now()));
        return productRepository.save(product);
    }

    public void updateProduct(ProductDTO productDTO, Long id) throws NoSuchElementException {
            Optional<Product> product = productRepository.findById(id);
            if(product.isPresent()){
                product.get().setName(productDTO.getName());
                product.get().setPrice((int) productDTO.getPrice());
                product.get().setImgPath(productDTO.getImgPath());
                product.get().setQuantity(productDTO.getQuantity());
                product.get().setToDate(Date.from(Instant.now()));
                productRepository.save(product.get());
            } else {
                throw new NoSuchElementException("No such element");
            }
    }

    @Transactional
    public void deleteCartItem(Long cartItemId) {
        cartItemsRepository.deleteItem(cartItemId);
    }

    public void incrementQuantity(Long cartItemId) {
        Cart_Items cart_items = cartItemsRepository.getOne(cartItemId);
        Integer newQuantity = cart_items.getQuantity() + 1;
        cart_items.setQuantity(newQuantity);
        cartItemsRepository.save(cart_items);
    }

    public void decrementQuantity(Long cartItemId) {
        Cart_Items cart_items = cartItemsRepository.getOne(cartItemId);
        Integer newQuantity = cart_items.getQuantity() - 1;
        cart_items.setQuantity(newQuantity);
        cartItemsRepository.save(cart_items);
    }

    public List<CartItemDTO> getCurrentUserItems() {
        return cartItemsRepository.findByUserId(myUserDetailService.getCurrentUser().getId()).stream().filter(ci ->
                ci.getProduct().getToDate() == null).map(ci -> dtoMappers.cartItemToMapper(ci)).collect(Collectors.toList());
    }
}
