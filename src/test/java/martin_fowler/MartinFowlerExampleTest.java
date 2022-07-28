package martin_fowler;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.injectors.AnnotatedFieldInjection;
import org.picocontainer.parameters.ConstantParameter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MartinFowlerExampleTest {

    @Test
    void testAvecConstructorInjection(){
            MutablePicoContainer pico = new DefaultPicoContainer();
            Parameter[] finderParams =  {new ConstantParameter("movies1.txt")};
            pico.addComponent(MovieFinder.class, ColonMovieFinder.class, finderParams);
            pico.addComponent(MovieLister.class);
            //We don't need to create the instance of MovieFinder ! It is done by the container.
            MovieLister lister = pico.getComponent(MovieLister.class);
            List<Movie> movies = lister.moviesDirectedBy("Sergio Leone");
            assertEquals("Once Upon a Time in the West",movies.get(0).getTitle());
            //we can access the instance of MovieFinder
            MovieFinder finder = pico.getComponent(MovieFinder.class);
            assertEquals("movies1.txt",finder.getFilename());
    }

    @Test
    void testAvecConstructorInjectionOtherImplementation(){
        MutablePicoContainer pico = new DefaultPicoContainer();
        Parameter[] finderParams =  {new ConstantParameter("movies2.txt")};
        //Change the implementation
        pico.addComponent(MovieFinder.class, SemiColonMovieFinder.class, finderParams);
        pico.addComponent(MovieLister.class);
        MovieLister lister = pico.getComponent(MovieLister.class);
        List<Movie> movies = lister.moviesDirectedBy("Sergio Leone");
        assertEquals("Once Upon a Time in the West",movies.get(0).getTitle());
        //we can access the instance of MovieFinder
        MovieFinder finder = pico.getComponent(MovieFinder.class);
        assertEquals("movies2.txt",finder.getFilename());
    }

    @Test
    void testSansInjection(){
        //We need to create the instance of MovieFinder and MovieLister
        MovieFinder finder = new ColonMovieFinder();
        finder.setFilename("movies1.txt");
        MovieLister lister = new MovieLister();
        //We need to inject the instance of MovieFinder
        //So that  we don't depend on a concrete implementation of MovieFinder
        //But we have to do injection manually
        lister.setFinder(finder);
        List<Movie> movies = lister.moviesDirectedBy("Sergio Leone");
        assertEquals("Once Upon a Time in the West",movies.get(0).getTitle());
    }

    @Test
    void testAvecFieldInjection(){
        MutablePicoContainer pico = new DefaultPicoContainer(new AnnotatedFieldInjection());
        Parameter[] finderParams =  {new ConstantParameter("movies3.txt")};
        pico.addComponent(MovieFinder.class, SpaceMovieFinder.class, finderParams);
        pico.addComponent(MovieListerByFieldInjection.class);
        MovieListerByFieldInjection lister = pico.getComponent(MovieListerByFieldInjection.class);
    List<Movie> movies = lister.moviesDirectedBy("Sergio Leone");
    assertEquals("Once Upon a Time in the West",movies.get(0).getTitle());
    //we can access the instance of MovieFinder
    MovieFinder finder = pico.getComponent(MovieFinder.class);
    assertEquals("movies3.txt",finder.getFilename());
}
}

