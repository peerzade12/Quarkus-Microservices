package org.serviceone.service;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;
import org.serviceone.entity.Book;

public interface BookService {
    Uni<Response> giveDelayOf();

    Uni<Response> acceptABook(Book book);

    Uni<Response> throwAnException(int num);
}
