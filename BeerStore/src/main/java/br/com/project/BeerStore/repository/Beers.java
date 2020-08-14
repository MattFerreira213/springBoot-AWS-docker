package br.com.project.BeerStore.repository;

import br.com.project.BeerStore.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Beers extends JpaRepository<Beer, Long> {
}
