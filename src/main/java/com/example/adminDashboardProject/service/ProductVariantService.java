package com.example.adminDashboardProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adminDashboardProject.dto.BillingDTO;
import com.example.adminDashboardProject.dto.ProductVariantDTO;
import com.example.adminDashboardProject.entity.Product;
import com.example.adminDashboardProject.entity.ProductVariant;
import com.example.adminDashboardProject.repository.ProductRepository;
import com.example.adminDashboardProject.repository.ProductVariantRepository;

@Service
public class ProductVariantService {

	@Autowired
	private ProductVariantRepository productVariantRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	public ProductVariant updateVariant(ProductVariant newProductVariant) {
		ProductVariant availVariant = productVariantRepo.findById(newProductVariant.getId()).orElse(null);
		
		if(availVariant == null) {
			return null;
		}
		
		availVariant.setStockQuantity(newProductVariant.getStockQuantity());
		
		return productVariantRepo.save(availVariant);
		
	}
	
	public ProductVariant reduceStockOfVariant(ProductVariant newProductVariant) {
		ProductVariant availVariant = productVariantRepo.findById(newProductVariant.getId()).orElse(null);
		
		if(availVariant == null) {
			return null;
		}
		
		availVariant.setStockQuantity(availVariant.getStockQuantity() - newProductVariant.getStockQuantity());
		
		return productVariantRepo.save(availVariant);
		
	}
	
	public Boolean updateListOfVariants(List<BillingDTO> billingDto) {
		for(BillingDTO billDto : billingDto) {
			ProductVariant newVariant = new ProductVariant();
			
			newVariant.setId(billDto.getVariantId());
			newVariant.setStockQuantity(billDto.getQuantity());
			
			if(reduceStockOfVariant(newVariant) == null) {
				return false;
			}
		}
		return true;	
	}
	
	public Boolean deleteVariant(Long variantId) {
		ProductVariant variant = productVariantRepo.findById(variantId).orElse(null);
		if(variant == null) {
			return false;
		}
		
		productVariantRepo.deleteById(variantId);
		return true;
	}
	
	public List<ProductVariant> getAllVariantsByProductId(Long productId) {
		return productVariantRepo.findAllByProductId(productId);
	}
	
	public void addVariantsToProduct(List<ProductVariantDTO> dtos) {
	    for (ProductVariantDTO dto : dtos) {
	        // Use getReferenceById to get a "Proxy" (faster than findById)
	        Product product = productRepo.getReferenceById(dto.getProductId());
	        
	        ProductVariant variant = new ProductVariant();
	        variant.setColor(dto.getColor());
	        variant.setSize(dto.getSize());
	        variant.setAdditionalPrice(dto.getAdditionalPrice());
	        variant.setStockQuantity(dto.getStockQuantity());
	        variant.setProduct(product); // Link the proxy product
	        
	        productVariantRepo.save(variant);
	    }
	}
}

