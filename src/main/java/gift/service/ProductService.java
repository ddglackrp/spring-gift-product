package gift.service;

import gift.domain.Product;
import gift.dto.request.ProductRequestDto;
import gift.dto.response.ProductResponseDto;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long addProduct(ProductRequestDto productDto){
        Product product = Product.toEntity(productDto);

        productRepository.save(product);

        return product.getId();
    }

    public ProductResponseDto findProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품은 존재하지 않습니다."));
        return ProductResponseDto.from(product);
    }

    public List<ProductResponseDto> findAllProducts(){
        return productRepository.findAll().stream()
                .map(ProductResponseDto::from)
                .collect(Collectors.toList());
    }

    public void updateProduct(Long id, int price){
        int updatedRow = productRepository.update(id, price);

        if(updatedRow == 0){
            throw new NoSuchElementException("해당 상품은 존재하지 않습니다.");
        }
    }

    public void deleteProduct(Long id){
        int deletedRow = productRepository.delete(id);

        if(deletedRow == 0){
            throw new NoSuchElementException("해당 상품은 존재하지 않습니다.");
        }
    }
}
