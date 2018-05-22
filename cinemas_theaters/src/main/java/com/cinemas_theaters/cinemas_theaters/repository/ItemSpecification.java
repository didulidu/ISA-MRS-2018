package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import com.cinemas_theaters.cinemas_theaters.domain.enums.Category;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class ItemSpecification implements Specification<Item> {


    private SearchCriteria searchCriteria;

    public ItemSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        if (searchCriteria.getOperation().equalsIgnoreCase(">")){
            return criteriaBuilder.greaterThanOrEqualTo(
                    root.<String> get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
        else if (searchCriteria.getOperation().equalsIgnoreCase("<")){
            return criteriaBuilder.lessThanOrEqualTo(
                    root.<String> get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
        else if (searchCriteria.getOperation().equalsIgnoreCase(":")){
            if (root.get(searchCriteria.getKey()).getJavaType() == String.class){
                return criteriaBuilder.like(
                        root.<String> get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
            }
            else{
                return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            }
        }
        else if (searchCriteria.getOperation().equalsIgnoreCase("::")){

            return criteriaBuilder.isMember(Category.valueOf(searchCriteria.getValue().toString()),root.get(searchCriteria.getKey()));
        }

        return null;
    }
}
