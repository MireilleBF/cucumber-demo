package martin_fowler;


import java.util.*;
// https://www.google.fr/url?sa=t&rct=j&q=&esrc=s&source=web&cd=3&ved=0ahUKEwj9ptek4q_QAhXHthoKHf28DPEQFggpMAI&url=http%3A%2F%2Fwww.cnc.fr%2Fc%2Fdocument_library%2Fget_file%3Fuuid%3D3adc5d0e-e89a-4404-8367-428973fb1560%26groupId%3D18&usg=AFQjCNF8hY-FiHZEPSOZUHcb-3TGO_tdgg&sig2=mXtqkbOdIFCaVfFq-Ixo6A&cad=rja

public interface MovieFinder {
    List<Movie> findAll();


    //To test without constructor injection
    void setFilename(String filename);

    String getFilename();
}