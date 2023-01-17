
import com.datawise.satisactual.entities.master.ProdCertificateEmbeddedKey;
import com.datawise.satisactual.entities.master.ProdCertificateEntity;
import com.datawise.satisactual.repository.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdCertificateRepository extends IBaseRepository<ProdCertificateEntity, ProdCertificateEmbeddedKey> {}
