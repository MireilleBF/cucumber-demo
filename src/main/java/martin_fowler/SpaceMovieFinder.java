package martin_fowler;


import org.picocontainer.annotations.Inject;

import java.util.ArrayList;
import java.util.List;

//Used to test the field injection
public class SpaceMovieFinder implements MovieFinder {

  @Inject
  private String filename;

  @Override
  public String getFilename() {
    return filename;
  }
  public SpaceMovieFinder() {
  }

  //TO test the constructor injection
  //public SpaceMovieFinder(String filename) {
  //  this.filename = filename;
  //}

  public void setFilename(String filename) {
    //never go by this way
    assert false;
    this.filename = filename;
  }
  
  public List<Movie> findAll(){
       System.out.println("SpaceMovieFinder ...");
    List<Movie> list = new ArrayList<Movie>();
    // TODO : Change it to something else
    list.add(new Movie("Dans les for�ts de Sib�rie","NEBBOU Safy"));
    list.add(new Movie("Demain","LAURENT M�lanie / DION Cyril"));
    list.add(new Movie("Once Upon a Time in the West","Sergio Leone"));
    return list;
  }
  
  public String toString(){
    return "SpaceMovieFinder";
  }
}
