package br.com.viniciustestezup.nossobancodigital.conta.nova.shared;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class Location {

    private Location() {}

    static public URI create(String pathUrl) {
        return ServletUriComponentsBuilder
                        .fromCurrentServletMapping()
                        .path(pathUrl)
                        .buildAndExpand()
                        .toUri();
    }
}
