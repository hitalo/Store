package com.hit.store.repositories.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hit.store.models.product.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{}
