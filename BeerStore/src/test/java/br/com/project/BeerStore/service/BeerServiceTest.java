package br.com.project.BeerStore.service;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

import br.com.project.BeerStore.model.Beer;
import br.com.project.BeerStore.model.BeerType;
import br.com.project.BeerStore.repository.Beers;
import br.com.project.BeerStore.service.exception.BeerAlreadExistException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

public class BeerServiceTest {

    private BeerService beerService;

    @Mock
    private Beers beersMocked;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        beerService = new BeerService(beersMocked);
    }

    @Test(expected = BeerAlreadExistException.class)
    public void should_deny_creation_of_beer_that_exists() {
        Beer beerInDatabase = new Beer();
        beerInDatabase.setId(10L);
        beerInDatabase.setName("Heineken");
        beerInDatabase.setVolume(new BigDecimal("450"));
        beerInDatabase.setType(BeerType.LAGER);

        when(beersMocked.findByNameAndType("Heineken", BeerType.LAGER)).thenReturn(Optional.of(beerInDatabase));

        Beer newBeer = new Beer();
        newBeer.setName("Heineken");
        newBeer.setType(BeerType.LAGER);
        newBeer.setVolume(new BigDecimal("450"));

        beerService.save(newBeer);
    }

    @Test
    public void should_create_new_beer(){
        Beer newBeer = new Beer();
        newBeer.setName("Heineken");
        newBeer.setType(BeerType.LAGER);
        newBeer.setVolume(new BigDecimal("450"));

        Beer newBeerInDatabase = new Beer();
        newBeerInDatabase.setId(10L);
        newBeerInDatabase.setName("Heineken");
        newBeerInDatabase.setType(BeerType.LAGER);
        when(beersMocked.save(newBeer)).thenReturn(newBeerInDatabase);
        Beer beerSaver = beerService.save(newBeer);

        assertThat(beerSaver.getId(), equalTo(10L));
        assertThat(beerSaver.getName(), equalTo("Heineken"));
        assertThat(beerSaver.getType(), equalTo(BeerType.LAGER));
    }
}
