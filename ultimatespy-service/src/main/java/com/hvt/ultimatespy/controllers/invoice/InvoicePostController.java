package com.hvt.ultimatespy.controllers.invoice;

import com.hvt.ultimatespy.models.invoice.Invoice;
import com.hvt.ultimatespy.services.invoice.InvoiceService;
import com.hvt.ultimatespy.utils.Constants;
import com.hvt.ultimatespy.utils.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = Constants.ROUTE_INVOICE)
public class InvoicePostController {

    @Autowired
    private InvoiceService invoiceService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Invoice> get(@RequestBody Invoice invoice) throws Exception {
        if (invoice.getUserId() == null || invoice.getUserId().isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;
        if (invoice.getProductId() == null || invoice.getProductId().isEmpty()) throw Errors.BAD_REQUEST_EXCEPTION;
        if (invoice.getAmount() == null || invoice.getAmount() < 0) throw Errors.BAD_REQUEST_EXCEPTION;

        invoice.setId(Constants.INVOICE_ID_PREFIX + UUID.randomUUID().toString().replaceAll("-", ""));
        Invoice result = invoiceService.insert(invoice).get();
        return ResponseEntity.ok(result);
    }

}
