package com.excilys.formation.util;

import com.excilys.formation.entity.Computer;
import com.excilys.formation.mapper.ComputerAndDtoMapper;
import com.excilys.formation.pagination.Page;
import com.excilys.formation.pagination.PageConstraints;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.excilys.formation.dto.ComputerDto;

import java.util.List;

/**
 * Created by excilys on 06/01/17.
 */
public class MenuActions {

    private static final String URL ="localhost:8081" ;

    Client client = ClientBuilder.newBuilder().register(new JacksonFeature()).build();
    /*

    public Page<ComputerDto> getPageComputer(PageConstraints pageConstraints) {
        if (pageConstraints != null) {
            WebTarget webTarget = client.target(URL + "/computer?page=" + pageConstraints.getCurrentPage() + "&size=" + pageConstraints.getPerPage());
            return ComputerAndDtoMapper.computerPageToDtoPage(webTarget.request().accept(MediaType.APPLICATION_JSON_TYPE).header("Content-type", "application/json").get().readEntity(new GenericType<Page<Computer>>(){}));
        }
        return null;
    }

    protected List<ComputerDto> getPage(int page, int number) {
        WebTarget webTarget = client.target(URL + "/computer?page=" + page + "&size=" + number);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        if (response.getStatus() == 200) {
            return response.readEntity(new GenericType<List<ComputerDto>>() {});

        } else {
            System.out.println("Error with rest api");
            return null;
        }
    }*/
}
