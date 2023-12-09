import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { EmailService } from 'src/app/service/email/email.service';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';
import { saveAs } from 'file-saver';
import { NotificationService } from 'src/app/service/util/notification.service';

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
  }

  isTemplatePresent(name: string){
    return this.availableTemplatesForTenant.includes(name);
  }

  selectTemplateName(name: string){
    this.selectedTemplate = name;
  }

  getPlacehHolderList(){
    if(CommonUtil.isNullOrEmptyOrUndefined(this.selectedTemplate)){
      return new Array();
    }
    return this.templates[this.selectedTemplate];
  }

  validateTemplate(){
    //TODO: validate if all placeholders present
  }

  downloadTemplete(){
    this.spinner.show();
    this.emailService.downloadTemplate(this.selectedTemplate)
        .subscribe(
          {
            next: async (resp: any) => {
              let contentDisposition = resp.headers.get('content-disposition');
              let filename = contentDisposition.split(';')[1].split('filename')[1].split('=')[1].trim();
              console.log(filename);
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
    this.emailService.uploadTemplate(this.selectedTemplateFile, this.selectedTemplate)
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
