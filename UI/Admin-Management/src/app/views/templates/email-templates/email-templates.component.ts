import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { EmailService } from 'src/app/service/email/email.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';
import { saveAs } from 'file-saver';
import { NotificationService } from 'src/app/service/util/notification.service';
import { transpileModule } from 'typescript/lib/tsserverlibrary';

@Component({
  selector: 'app-email-templates',
  templateUrl: './email-templates.component.html',
  styleUrls: ['./email-templates.component.scss']
})
export class EmailTemplatesComponent implements OnInit {

  templates: any;
  templateNames: any;
  selectedTemplate!: string;
  fileContent: any;
  selectedTemplateFile!: File;
  availableTemplatesForTenant: any[] = new Array();

  productTemplates: any;
  productTemplateNames: any;
  availableProductTemplatesForTenant: any[] = new Array();

  tenantTemplate = false;
  productTemplate = false;

  constructor(private emailService: EmailService, private spinner: SpinnerService, private sanitizer: DomSanitizer,
              private notification: NotificationService){
    
  }

  ngOnInit(): void {
    this.spinner.show();
    this.emailService.getAllEmailTemplateNames()
        .subscribe({
          next: (resp:any) => {
            this.templates = resp.data;
            this.templateNames = Object.keys(this.templates);
            this.availableTemplatesForTenant = resp.dataList;
          },
          error: (err: any) => {

          }, complete :() => {
            this.spinner.hide();
          }
        })
    this.emailService.getAllProductEmailTemplateNames()
        .subscribe({
          next: (resp:any) => {
            this.productTemplates = resp.data;
            this.productTemplateNames = Object.keys(this.productTemplates);
            this.availableProductTemplatesForTenant = resp.dataList;
          },
          error: (err: any) => {

          }, complete :() => {
            this.spinner.hide();
          }
        })
  }

  isTemplatePresent(name: string){
    return this.availableTemplatesForTenant.includes(name) || this.availableProductTemplatesForTenant.includes(name);
  }

  selectTemplateName(name: string){
    this.selectedTemplate = name;
    //TODO: bad way, find a optimized way to handle this scenario.
    this.tenantTemplate = true;
    this.productTemplate = false;
  }

  selectProductTemplateName(name: string){
    this.selectedTemplate = name;
    this.tenantTemplate = false;
    this.productTemplate = true;
  }

  canEnableButton(){
    return CommonUtil.isNullOrEmptyOrUndefined(this.selectedTemplate);
  }

  getPlacehHolderList(){
    if(CommonUtil.isNullOrEmptyOrUndefined(this.selectedTemplate)){
      return new Array();
    }
    if(this.tenantTemplate){
      return this.templates[this.selectedTemplate];
    }
    else {
      return this.productTemplates[this.selectedTemplate];
    }
  }

  validateTemplate(){
    //TODO: validate if all placeholders present
  }

  getService(): string{
    if(this.tenantTemplate){
      return "TM";
    }
    else if (this.productTemplate){
      return "PM";
    }
    return "TM";
  }

  downloadTemplete(){
    this.spinner.show();
    this.emailService.downloadTemplate(this.selectedTemplate, this.getService())
        .subscribe(
          {
            next: async (resp: any) => {
              let contentDisposition = resp.headers.get('content-disposition');
              let filename = contentDisposition.split(';')[1].split('filename')[1].split('=')[1].trim();
              saveAs(resp.body, filename);
            },
            error: (err: any) => {
              this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
              this.spinner.hide();
            },
            complete: () => {
              this.spinner.hide();
            }
          }
        )
  }

  uploadTemplate(){
    this.spinner.show();
    this.emailService.uploadTemplate(this.selectedTemplateFile, this.selectedTemplate, this.getService())
        .subscribe({
          next:(resp: any) =>{
            this.availableTemplatesForTenant.push(this.selectedTemplate);
          },
          error: (err: any) =>{
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          },
          complete: ()=>{
            this.spinner.hide();
          }
        })
  }

  onTemplateSelected(event: any){
    this.selectedTemplateFile = event.target.files[0];
    let fileReader = new FileReader();
    fileReader.onload = (e) => {
      let content: any = fileReader.result;
      this.fileContent = this.sanitizer.bypassSecurityTrustHtml(content);
    }
    fileReader.readAsText(this.selectedTemplateFile);
  }

}
