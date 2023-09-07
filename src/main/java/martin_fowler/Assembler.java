package martin_fowler;


import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.Parameter;
import org.picocontainer.injectors.AnnotatedFieldInjection;
import org.picocontainer.parameters.ConstantParameter;

import java.util.*;

public class Assembler{

  private MutablePicoContainer configureContainer() {
    MutablePicoContainer pico = new DefaultPicoContainer();
    Parameter[] finderParams =  {new ConstantParameter("movies1.txt")};
    pico.addComponent(MovieFinder.class, ColonMovieFinder.class, finderParams);
    pico.addComponent(MovieLister.class);
    return pico;
  }

  private MutablePicoContainer configureContainerWithFieldInjection() {
    MutablePicoContainer pico = new DefaultPicoContainer(new AnnotatedFieldInjection());
    Parameter[] finderParams =  {new ConstantParameter("movies3.txt")};
    pico.addComponent(MovieFinder.class, SpaceMovieFinder.class, finderParams);
    pico.addComponent(MovieListerByFieldInjection.class);
   return pico;
  }
  public static void main(String[] args){
    Assembler assembler = new Assembler();
    MutablePicoContainer pico = assembler.configureContainer();
    MovieLister lister = pico.getComponent(MovieLister.class);
    List<Movie> movies = lister.moviesDirectedBy("Sergio Leone");
    System.out.println(movies.get(0).getTitle());

    MutablePicoContainer pico2 = assembler.configureContainerWithFieldInjection();
    MovieListerByFieldInjection lister2 = pico2.getComponent(MovieListerByFieldInjection.class);
    List<Movie> movies2 = lister.moviesDirectedBy("Sergio Leone");
    System.out.println(movies2.get(0).getTitle());
  }
}
