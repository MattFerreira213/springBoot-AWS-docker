package br.com.project.BeerStore.repository;

import br.com.project.BeerStore.model.Beer;
import br.com.project.BeerStore.model.BeerType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Beers extends JpaRepository<Beer, Long> {

    Optional<Beer> findByNameAndType(String name, BeerType type);

}
