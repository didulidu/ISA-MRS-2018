package com.cinemas_theaters.cinemas_theaters.repository;

import com.cinemas_theaters.cinemas_theaters.domain.entity.Item;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;

import java.util.ArrayList;
import java.util.List;

public class ItemSpecificationsBuilder {

    private final List<SearchCriteria> params;

    public ItemSpecificationsBuilder(){
        params = new ArrayList<SearchCriteria>();
    }

    public ItemSpecificationsBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public Specification<Item> build(){
        if (params.size() == 0){
            return null;
        }

        List<Specification<Item>> specs = new ArrayList<>();
        for (SearchCriteria param : params){
            specs.add(new ItemSpecification(param));
        }

        Specification<Item> result = specs.get(0);
        for (int i = 1; i < specs.size(); i++) {
            result = Specifications.where(result).and(specs.get(i));
        }
        return result;
    }
}
