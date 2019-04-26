package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Invoice;
import com.mycompany.myapp.repository.InvoiceRepository;
import com.mycompany.myapp.service.dto.InvoiceDTO;
import com.mycompany.myapp.service.mapper.InvoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Invoice}.
 */
@Service
@Transactional
public class InvoiceService {

    private final Logger log = LoggerFactory.getLogger(InvoiceService.class);

    private final InvoiceRepository invoiceRepository;

    private final InvoiceMapper invoiceMapper;
    private final AmazonService amazonService;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper, AmazonService amazonService) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.amazonService = amazonService;
    }

    /**
     * Save a invoice.
     *
     * @param invoiceDTO the entity to save.
     * @return the persisted entity.
     */
    public InvoiceDTO save(InvoiceDTO invoiceDTO) {
        log.debug("Request to save Invoice : {}", invoiceDTO);

        byte[] pdf = invoiceDTO.getPdf();
        invoiceDTO.setPdf(null);
        if (pdf != null) {
            String fileName = invoiceDTO.getCompanyId() + "/" + UUID.randomUUID() + ".pdf";
            String url = amazonService.uploadFile(pdf, fileName);
            invoiceDTO.setAttachmentUrl(url);
        }
        
//        Invoice invoice = invoiceFaMapper.toEntity(invoiceDTO);
//        invoice = invoiceFaRepository.save(invoice);

//        documentChangeService.save(STATUS, "", "CREATED", invoice.getId());
//        if (invoice.getStatus().equals(Status.OPEN)) {
//            documentChangeService.save(STATUS, "", "CONFIRMED", invoice.getId());
//        }
//        invoice.getLines().clear();
//        for (InvoiceLineDTO line : invoiceDTO.getLines()) {
//            line.setInvoiceId(invoice.getId());
//            line = invoiceLineService.create(line);
//            invoice.getLines().add(invoiceLineMapper.toEntity(line));
//        }

// I tried this also, but doesn't work
//        invoice.setPdf(null);
//        invoiceFaRepository.save(invoice);
//        return invoiceFaMapper.toDto(invoice);



        Invoice invoice = invoiceMapper.toEntity(invoiceDTO);
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toDto(invoice);
    }

    /**
     * Get all the invoices.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InvoiceDTO> findAll() {
        log.debug("Request to get all Invoices");
        return invoiceRepository.findAll().stream()
            .map(invoiceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one invoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceDTO> findOne(Long id) {
        log.debug("Request to get Invoice : {}", id);
        return invoiceRepository.findById(id)
            .map(invoiceMapper::toDto);
    }

    /**
     * Delete the invoice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Invoice : {}", id);
        invoiceRepository.deleteById(id);
    }
}
