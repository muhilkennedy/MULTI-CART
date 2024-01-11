import { Injectable } from "@angular/core";

import * as pdfMake from 'pdfmake/build/pdfmake.js';
import * as pdfFonts from 'pdfmake/build/vfs_fonts.js';
import { Invoice } from '../../model/invoice.model';
import { environment } from "src/environments/environment";

pdfMake.vfs = pdfFonts.pdfMake.vfs;

@Injectable({
    providedIn: 'root'
})
export class PdfUtil {

      static generatePDF(invoice: Invoice, action = 'open', size='80') {
        
        //155 for 58 : 53mm
        //220 for 80mm
        let docDefinition;
        if(size == '80'){
          docDefinition = PdfUtil.get80mmDocDefinition(invoice);
        }
        else if(size == '58'){      
          docDefinition = PdfUtil.get58mmDocDefinition(invoice);
        }
        else{
          alert("invalid print size");
          return;
        }
    
         if(action==='download'){
          pdfMake.createPdf(docDefinition).download();    
        }else if(action === 'print'){    
          pdfMake.createPdf(docDefinition).print();          
        }else{    
          pdfMake.createPdf(docDefinition).open();          
        } 
      }

      static get58mmDocDefinition(invoice: Invoice){
        return {
          pageSize: {
            width: 155,
            height: 'auto'
          },
          pageMargins: {
            left: 1,
            right: 1,
            top: 1,
            bottom: 1
          },
          content: [
            {
              text: environment.tenant,
              fontSize: 15,
              alignment: 'center',
              color: '#047886',
              bold: true
            },
            {
              text: 'POS INVOICE',
              fontSize: 10,
              bold: true,
              alignment: 'center',
              decoration: 'underline',
            },
            // {
            //   text: 'Invoice Details:',
            //   style: 'sectionHeader'
            // },
            {
              table: {
                headerRows: 1,
                dontBreakRows: false,
                widths: [35, 5, 85],
                body: [
                  ['Bill No', ':', invoice.invoiceId],
                  ['Bill By', ':', invoice.billingUser],
                  ['Date', ':', new Date().toLocaleString()],     
                ]
              },
              layout: 'noBorders'
            },
            // {
            //   text: 'Order Details',
            //   style: 'sectionHeader'
            // },
            {
              table: {
                headerRows: 1,
                dontBreakRows: false,
                widths: [40, 30, 20, 35],
                body: [
                  ['Item', 'MRP', 'Qty', 'Price'],
                  ...invoice.products.map(p => ([p.productName, p.mrp, p.quantity, (p.mrp*p.quantity).toFixed(2)])),
                  [{text: 'SubTotal (MRP)', colSpan: 2}, {}, {text: 0, colSpan: 2}],
                  [{text: 'Total Discount', colSpan: 2}, {}, {text: 0, colSpan: 2}],
                  [{text: 'Total Amount', colSpan: 2}, {}, {text: invoice.products.reduce((sum, p)=> sum + (p.quantity * p.mrp), 0).toFixed(2), colSpan: 2}]
                ]
              }
            },
            // {
            //   text: 'Additional Details',
            //   style: 'sectionHeader'
            // },
            {
              fontSize: 8,
              table: {
                headerRows: 1,
                dontBreakRows: false,
                widths: [60, 5, 65],
                body: [
                  ['Payment Mode', ':', 'paytm'],
                  ['Contact', ':', 'xxxx'],
                  ['Address', ':', 'xxxx xxxxx xxxxx xxxxxxxxx'],    
                ]
              },
              layout: 'noBorders'
            },
            // {
            //     text: "this.invoice.additionalDetails",
            //     // margin: [0, 0 ,0, 15]          
            // },
            // {
            //   columns: [
            //     [{ qr: invoice.invoiceId, fit: '50' }], //encode url to open online bill
            //     [{ text: 'Signature', alignment: 'right', italics: true}],
            //   ]
            // },
            // {
            //   text: 'Terms and Conditions',
            //   style: 'sectionHeader'
            // },
            // {
            //     ul: [
            //       'Order can be return in max 10 days.',
            //       'Warrenty of the product will be subject to the manufacturer terms and conditions.',
            //       'This is system generated invoice.',
            //     ],
            // }
          ],
          styles: {
            sectionHeader: {
              bold: true,
              decoration: 'underline',
              fontSize: 14,
              // margin: [0, 15,0, 15]          
            }
          }
        };
      }

      static get80mmDocDefinition(invoice: Invoice){
        return {
          pageSize: {
            width: 220,
            height: 'auto'
          },
          pageMargins: {
            left: 1,
            right: 1,
            top: 1,
            bottom: 5
          },
          content: [
            {
              text: environment.tenant,
              fontSize: 15,
              alignment: 'center',
              color: '#047886',
              bold: true
            },
            {
              text: 'POS INVOICE',
              fontSize: 10,
              bold: true,
              alignment: 'center',
              decoration: 'underline',
            },
            {
              table: {
                headerRows: 1,
                dontBreakRows: false,
                widths: [35, 5, 130],
                body: [
                  ['Bill No', ':', invoice.invoiceId],
                  ['Bill By', ':', invoice.billingUser],
                  ['Date', ':', new Date().toLocaleString()],     
                ]
              },
              layout: 'noBorders'
            },
            {
              table: {
                headerRows: 1,
                dontBreakRows: false,
                widths: [70, 35, 25, 50],
                body: [
                  ['Item', 'MRP', 'Qty', 'Price'],
                  ...invoice.products.map(p => ([p.productName, p.mrp, p.quantity, (p.mrp*p.quantity).toFixed(2)])),
                  [{text: 'SubTotal (MRP)', colSpan: 2}, {}, {text: 0, colSpan: 2}],
                  [{text: 'Total Discount', colSpan: 2}, {}, {text: 0, colSpan: 2}],
                  [{text: 'Total Amount', colSpan: 2}, {}, {text: invoice.products.reduce((sum, p)=> sum + (p.quantity * p.mrp), 0).toFixed(2), colSpan: 2}]
                ]
              }
            },
            {
              fontSize: 10,
              table: {
                headerRows: 1,
                dontBreakRows: false,
                widths: [70, 5, 135],
                body: [
                  ['Payment Mode', ':', 'paytm'],
                  ['Contact', ':', 'xxxx'],
                  ['Address', ':', 'xxxx xxxxx xxxxx xxxxxxxxx'],    
                ]
              },
              layout: 'noBorders'
            },
            {
              columns: [
                [
                  { 
                    qr: invoice.invoiceId,
                    fit: '100',
                    alignment: 'center',
                  }
                ]
              ]
            }
          ],
          styles: {
            sectionHeader: {
              bold: true,
              decoration: 'underline',
              fontSize: 14,
              // margin: [0, 15,0, 15]          
            }
          }
        };
      }
}