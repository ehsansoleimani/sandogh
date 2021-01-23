package com.sandogh.sandogh.customers.dao;

import com.sandogh.sandogh.customers.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
@Repository
public interface CustomerDAO extends CrudRepository<Customer, Long> {
}
