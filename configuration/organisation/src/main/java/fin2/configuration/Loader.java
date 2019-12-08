package fin2.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import javafx.util.Pair;

import java.util.*;

public class Loader<T> {
    public static Logger logger = LoggerFactory.getLogger(Loader.class);

    protected CrudRepository<T,String> repo;
    protected SubVal<T> service;
    protected List<T> list = new ArrayList<>();
    protected Iterator<T> iterator;
    protected String name;
    static protected Set<String> processed = new HashSet<>();
    static protected List<Pair<String,String>> dependency = new ArrayList<>();

    protected Loader(String name, SubVal<T> service, CrudRepository<T,String> repo) {
        logger.warn("Initializing {}",name);
        this.name = name;
        this.service = service;
        this.repo = repo;
        repo.deleteAll();
    }

    protected void dependsOn(String name) {
        dependency.add( new Pair<>(this.name, name));
    }
    protected boolean checkDependencies() {
        boolean ok = true;
        for (Pair<String,String> pair : dependency) {
            if ( pair.getKey().equals(this.name) ) {
                String dep = pair.getValue();
                if (! processed.contains(dep) ) {
                    ok = false;
                    logger.warn("Dependency of {} from {} not yet satisfied",pair.getKey(),dep);
                }
            }
        }
        return ok;
    }

    protected void add(T entry) {
        list.add(entry);
    }
    private void init() throws Exception{
        if (checkDependencies()) {
            iterator = list.iterator();
            for (T entry : list) {
                entry = service.subVal(entry, false);
                repo.save(entry);
            }
            processed.add(name);
        }
    }

    public T next() {
        if (iterator == null) {
            try {
                init();
                if (iterator == null) {
                  //still null --> dependency not loaded yet
                  return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (iterator.hasNext()) {
            T entry = iterator.next();
            logger.warn("Load {}",entry);
            return entry;
        } else {
            logger.info("Loading done");
            return null;
        }
    }
}
