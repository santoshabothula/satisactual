
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdCertificateEmbeddedKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cod_certificate")
    private String codCertificate;

    @Column(name = "cod_rec_status")
    private String codRecordStatus;
}