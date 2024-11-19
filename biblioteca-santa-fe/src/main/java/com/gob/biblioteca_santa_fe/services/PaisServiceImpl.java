package com.gob.biblioteca_santa_fe.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gob.biblioteca_santa_fe.interfaces.PaisService;
import com.gob.biblioteca_santa_fe.model.Pais;
import com.gob.biblioteca_santa_fe.repository.PaisRepository;

@Service
public class PaisServiceImpl implements PaisService {
    @Autowired
    private PaisRepository paisRepository;

    @Override
    public List<Pais> findAll() {
        return paisRepository.findAll();
    }
}
