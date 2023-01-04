package academy.digitallab.store.invoiceservice.repositories;

import academy.digitallab.store.invoiceservice.entities.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem,Long> {
}
