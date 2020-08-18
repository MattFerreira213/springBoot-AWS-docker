package br.com.project.BeerStore.service;

import br.com.project.BeerStore.model.Beer;
import br.com.project.BeerStore.repository.Beers;
import br.com.project.BeerStore.service.exception.BeerAlreadExistException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BeerService {

    private Beers beers;

    public BeerService( @Autowired Beers beers){
        this.beers = beers;
    }

   public Beer save(final Beer beer){
       Optional<Beer> beerByNameAndType = beers.findByNameAndType(beer.getName(), beer.getType());

       if(beerByNameAndType.isPresent()){
           throw new BeerAlreadExistException();
       }

       return beers.save(beer);
   }
}
