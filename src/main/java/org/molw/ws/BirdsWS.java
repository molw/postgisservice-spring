package org.molw.ws;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.molw.data.BirdobsEntity;
import org.molw.data.BirdsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by jmorales on 05/03/2017.
 */
@RequestMapping("/ws/birds")
@RestController
public class BirdsWS {

	@Autowired
  private BirdsRepository repository;
  
  @CrossOrigin
  @RequestMapping(method = RequestMethod.GET, value = "/byobserver/{id}", produces = "application/json")
  public List<Map<String, Object>> getObserverByID(@RequestParam("id") String id) {
    List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

    List<BirdobsEntity> birds = repository.findByObserverId(id);

    Iterator<BirdobsEntity> birdIterator = birds.iterator();
    while (birdIterator.hasNext()) {
      Map<String, Object> result = new HashMap<String, Object>();
      BirdobsEntity bird = birdIterator.next();

      //Deal with the coords
      List<String> coordArray = new ArrayList<String>();

      String coords = bird.getLocation().getCoordinate().toString().replaceFirst(", NaN", "");
      //"(-121.9602598, 36.9653195)"
      int firstComma = coords.indexOf(',');
      coordArray.add(coords.substring(1, firstComma));
      coordArray.add(coords.substring(firstComma + 1, coords.length() - 1));
      result.put("coords", coordArray);
      result.put("id", bird.getObserverId());
      result.put("commonName", bird.getCommonName());
      result.put("startTime", bird.getObservationStart().toString());
      results.add(result);
    }

    return results;
  }

  /*
  @CrossOrigin
  @RequestMapping(method = RequestMethod.GET, value = "/byspecies/{commonName}", produces = "application/json")
  public  ArrayList getRecordsByCommonName(@RequestParam("commonName") String commonName) throws NamingException{
      List<BirdobsEntity> birds;
      em = getEntityManager();
      em.getTransaction().begin();
      birds = em.createQuery("SELECT b FROM BirdobsEntity b WHERE b.commonName = :commonName ").setParameter("commonName", commonName).getResultList();
      em.getTransaction().commit();

      ArrayList results = new ArrayList();
      Iterator<BirdobsEntity> birdIterator = birds.iterator();
      while (birdIterator.hasNext()){
        HashMap<String, Object> result = new HashMap<String, Object>();
        BirdobsEntity bird = birdIterator.next();

        //Deal with the coords
        ArrayList<String> coordArray = new ArrayList<String>();

        String coords = bird.getLocation().getCoordinate().toString().replaceFirst(", NaN", "");
        //"(-121.9602598, 36.9653195)"
        int firstComma = coords.indexOf(',');
        coordArray.add(coords.substring(1, firstComma));
        coordArray.add(coords.substring(firstComma+1, coords.length()-1));
        result.put("coords", coordArray);
        result.put("commonName", bird.getCommonName());
        Short numberSeen = bird.getObservationCount();
        if (numberSeen != null){
          result.put("numberSeen", numberSeen.toString());
        } else {
          result.put("numberSeen", "NA");
        }

        result.put("startTime", bird.getObservationStart().toString());
        results.add(result);
      }


      em.close();
      return results;
  }

*/
  @CrossOrigin
  @RequestMapping(method = RequestMethod.GET, value = "/fivebirds", produces = "application/json")
  public List<BirdobsEntity> getFiveBirds() {
    return repository.findAll(new PageRequest(0, 5));
  }

  @CrossOrigin
  @RequestMapping(method = RequestMethod.GET, value = "/json", produces = "application/json")
  public String getHelloWorldJSON() {
    System.out.printf("Hellow world from JSON");
    return "{\"result\":\"" + "World" + "\"}";
  }

  @CrossOrigin
  @RequestMapping(method = RequestMethod.GET, value = "/xml", produces = "application/xml")
  public String getHelloWorldXML() {
    return "<xml><result>world</result></xml>";
  }
}