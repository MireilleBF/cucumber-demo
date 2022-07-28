package martin_fowler;

public class Movie{
    private String title;
    private String director;

  public Movie(String title, String director){
    this.title = title;
    this.director = director;
  }
  
  public String getTitle(){
    return title;
  }
  
  public String getDirector(){
    return director;
  }
  
}
