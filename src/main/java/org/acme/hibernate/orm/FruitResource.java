package org.acme.hibernate.orm;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Path("fruits")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class FruitResource {

    private static final Logger LOGGER = Logger.getLogger(FruitResource.class.getName());

    @Inject
    EntityManager entityManager;
    
    
    @GET
    @Path("basket")
    public List<FruitAmount> getBasket() {
    	String sql = "SELECT f.id , f.name , b.amount "
    			+ " FROM known_fruits f "
    			+ " left join basket b on f.id = b.fruits_id "
    			+ " ORDER BY f.name";
    	
    	List<Object[]> objects = entityManager.createNativeQuery(sql).getResultList();
    	List<FruitAmount> fruits = new ArrayList<>();
    	for(Object[] object : objects) {
    		FruitAmount fruit = new FruitAmount();
    		fruit.setId((Integer)object[0]);
    		fruit.setName((String)object[1]);
    		fruit.setAmount((Integer)object[2]);
    		fruits.add(fruit);
    	}
    	return fruits;
    }

    @GET
    public List<Fruit> get() {
        // return entityManager.createNamedQuery("Fruits.findAll", Fruit.class).getResultList();
    	
    	
//    	List<Object[]> objects = entityManager.createNativeQuery("SELECT id , name FROM known_fruits f ORDER BY f.name").getResultList();
//    	List<Fruit> fruits = new ArrayList<>();
//    	for(Object[] object : objects) {
//    		Fruit fruit = new Fruit();
//    		fruit.setId((Integer)object[0]);
//    		fruit.setName((String)object[1]);
//    		fruits.add(fruit);
//    	}
//    	return fruits;
    	
    	return entityManager.createQuery("SELECT f FROM Fruit f ORDER BY f.name", Fruit.class).getResultList();
    }

    @GET
    @Path("{id}")
    public Fruit getSingle(Integer id) {
        Fruit entity = entityManager.find(Fruit.class, id);
        if (entity == null) {
            throw new WebApplicationException("Fruit with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Fruit fruit) {
        if (fruit.getId() != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        entityManager.persist(fruit);
        return Response.ok(fruit).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Fruit update(Integer id, Fruit fruit) {
        if (fruit.getName() == null) {
            throw new WebApplicationException("Fruit Name was not set on request.", 422);
        }

        Fruit entity = entityManager.find(Fruit.class, id);

        if (entity == null) {
            throw new WebApplicationException("Fruit with id of " + id + " does not exist.", 404);
        }

        entity.setName(fruit.getName());

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(Integer id) {
        Fruit entity = entityManager.getReference(Fruit.class, id);
        if (entity == null) {
            throw new WebApplicationException("Fruit with id of " + id + " does not exist.", 404);
        }
        entityManager.remove(entity);
        return Response.status(204).build();
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Failed to handle request", exception);

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", exception.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}
