package com.hvt.ultimatespy.controllers.invoice;

import com.hvt.ultimatespy.models.invoice.Invoice;
import com.hvt.ultimatespy.services.invoice.InvoiceService;
import com.hvt.ultimatespy.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Constants.ROUTE_INVOICE_ID)
public class InvoiceGetController {

    @Autowired
    private InvoiceService invoiceService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Invoice> get(@PathVariable String id) throws Exception {
        Invoice invoice = invoiceService.get(id).get();
        return ResponseEntity.ok(invoice);
    }

}
