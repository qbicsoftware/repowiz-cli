package life.application.spi;

import life.qbic.repowiz.application.spi.RepositoryLoaderJava;
import life.qbic.repowiz.spi.SubmissionManager;
import life.qbic.repowiz.spi.TargetRepositoryProvider;
//import life.qbic.repowiz.spiimpl.ClinvarTargetRepositoryProvider;
//import life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RepositoryLoaderJavaTest {
/*    @Test
    public void instantiateClass() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //given
        RepositoryLoaderJava loader = new RepositoryLoaderJava();
        String geoClass = "life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider";
        //when
        TargetRepositoryProvider res = loader.getClassInstance(geoClass);
        //then
        assert res instanceof GeoTargetRepositoryProvider;
    }

    @Test
    public void loadAllClasses() throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        //given
        RepositoryLoaderJava loader = new RepositoryLoaderJava();
        //when
        List<TargetRepositoryProvider> res = loader.load();
        //then
        assert res.size() == 2;
    }

    @Test
    public void loadsGeoTargetClass() throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        //given
        RepositoryLoaderJava loader = new RepositoryLoaderJava();
        String geoClass = "life.qbic.repowiz.spiimpl.GeoTargetRepositoryProvider";

        //when:
        TargetRepositoryProvider res = loader.load(geoClass);
        SubmissionManager manager = res.create();

        //then:
        assert res instanceof GeoTargetRepositoryProvider;
        assert manager != null;
    }

    @Test
    public void loadsClinvarTargetClass() throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        //given
        RepositoryLoaderJava loader = new RepositoryLoaderJava();
        String clinvarClass = "life.qbic.repowiz.spiimpl.ClinvarTargetRepositoryProvider";

        //when:
        TargetRepositoryProvider res = loader.load(clinvarClass);
        SubmissionManager manager = res.create();

        //then:
        assert res instanceof ClinvarTargetRepositoryProvider;
        assert manager != null;
    }*/

}
