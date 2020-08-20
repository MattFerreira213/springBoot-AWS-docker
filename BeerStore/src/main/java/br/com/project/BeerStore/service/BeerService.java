package br.com.project.BeerStore.service;

import br.com.project.BeerStore.model.Beer;
import br.com.project.BeerStore.repository.Beers;
import br.com.project.BeerStore.service.exception.BeerAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class BeerService {

    private Beers beers;

    public BeerService( @Autowired Beers beers){
        this.beers = beers;
    }

    public Beer save(final Beer beer) {
        verifyIfBeerExists(beer);
        return beers.save(beer);
    }

    private void verifyIfBeerExists(final Beer beer) {
        Optional<Beer> beerByNameAndType = beers.findByNameAndType
                (beer.getName(), beer.getType());

        if (beerByNameAndType.isPresent() && (beer.isNew() ||
                isUpdatingToADifferentBeer(beer, beerByNameAndType))) {
            throw new BeerAlreadyExistException();
        }
    }

    private boolean isUpdatingToADifferentBeer(Beer beer,
                                               Optional<Beer> beerByNameAndType) {
        return beer.alreadyExist() && !beerByNameAndType.get()
                .equals(beer);
    }

    public void delete(final Long id){
        Optional beerDel = beers.findById(id);
        if (!beerDel.isPresent()){
            throw new EntityNotFoundException();
        }
        beers.deleteById(id);
    }
}
