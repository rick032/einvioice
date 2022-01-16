package rick.einvioice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rick.einvioice.model.EinvTitleBean;
import rick.einvioice.model.Params;

import java.util.List;

@Repository
public interface ParamsRepository extends CrudRepository<Params, Long> {
    List<Params> findAll();
    @Query(value="select * from Params where P_K = ?1" ,nativeQuery = true)
    Params findByKey(String p_k);
    @Query(value="select P_V from Params where P_K = ?1" ,nativeQuery = true)
    String findValueByKey(String p_k);
}
