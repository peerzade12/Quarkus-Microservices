package org.serviceone.service.serviceimpl;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.serviceone.entity.Book;
import org.serviceone.service.BookService;

import java.time.Duration;
import java.util.logging.Logger;

@ApplicationScoped
public class BookServiceImpl implements BookService {

    private static final Logger LOG = Logger.getLogger("BookServiceImpl.class");
    private static final String DATA = "Welcome to Quarkus!";

    @ConfigProperty(name = "zero-division", defaultValue = "0")
    int zero;

    @Override
    public Uni<Response> giveDelayOf() {
        return Uni.createFrom()
                .item(DATA)
                .onItem().delayIt().by(Duration.ofSeconds(10))
                .map(result-> Response.ok(result).build());
    }

    @Override
    public Uni<Response> acceptABook(Book book) {
        return Uni.createFrom()
                .item(book)
                .map(result-> Response.ok("Success").build());
    }

    @Override
    public Uni<Response> throwAnException(int num) throws RuntimeException{
        try {
            int result = num / zero;
            return Uni.createFrom().item(result).onItem().transform(response->Response.ok(result).build());
        } catch (ArithmeticException e) {
            throw new RuntimeException("Custom ArithmeticException occurred!");
        }
    }


}
