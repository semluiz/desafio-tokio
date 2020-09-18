package com.example.api.service;


import com.example.api.model.CustomerAddress;
import com.example.api.model.ResponseZipCode;
import com.example.api.exception.UnfoundZipCodException;
import com.example.api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressService {

    @Autowired
    private AddressRepository enderecoRepository;

    public CustomerAddress buscaEnderecoPorCEP(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        String urlViaCep = "http://viacep.com.br/ws/" + cep + "/json";
        ResponseZipCode resp = restTemplate.getForObject(urlViaCep, ResponseZipCode.class);
        if ("true".equals(resp.getLog())) {
            throw new UnfoundZipCodException("Cep não encontrado");
        }
        CustomerAddress address = new CustomerAddress(resp.getCep(),resp.getBairro(),resp.getCidade());
        return address;
    }

    public CustomerAddress salvar(CustomerAddress customerAddress) {
        return enderecoRepository.save(customerAddress);
    }

}
