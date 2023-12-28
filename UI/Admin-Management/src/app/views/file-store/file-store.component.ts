import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { CommonUtil } from 'src/app/service/util/common-util.service';
import { NotificationService, NotificationType } from 'src/app/service/util/notification.service';
import { FileStoreService } from 'src/app/service/file-store/file-store.service';
import { SpinnerService } from 'src/app/service/util/sipnner.service';
import * as saveAs from 'file-saver';

@Component({
  selector: 'app-filestore',
  templateUrl: './file-store.component.html',
  styleUrls: ['./file-store.component.scss']
})
export class FileStoreComponent implements OnInit {

  showFileModal: boolean = false;
  totalFileSize: number = 0;
  totalFiles: number = 0;
  selectedFile!: File;
  internalAccess: boolean = false;

  files: any[] = new Array();
  displayedFilesColumns: string[] = ['FileName', 'FileExtension', 'FileSize', 'IsInternal', 'CreatedOn', 'FileUrl', 'Actions'];
  totalPages: number = 10;
  pageSize: number = 25;
  pageIndex: number = 0;

  constructor(private notification: NotificationService,
    private spinner: SpinnerService, private fileService: FileStoreService) {

  }

  ngOnInit(): void {
    this.load();
  }

  load() {
    this.loadFiles();
    this.getTotalUtilizedLimit();
  }

  handlePageEvent(e: PageEvent) {
    this.totalPages = e.length;
    this.pageSize = e.pageSize;
    this.pageIndex = e.pageIndex;
    this.loadFiles();
  }

  toggleAccessControl() {
    this.internalAccess = !this.internalAccess;
  }

  loadFiles() {
    this.spinner.show();
    this.fileService.getAllFiles(this.pageIndex, this.pageSize)
      .subscribe({
        next: (resp: any) => {
          this.files = resp.data.content;
          this.totalFiles = resp.data.totalElements;
          this.totalPages = resp.data.totalPages;
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        }
      })
  }

  getTotalUtilizedLimit() {
    this.fileService.getTotalUtilizedLimit()
      .subscribe({
        next: (resp: any) => {
          this.totalFileSize = resp.data;
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

  toggleFileModal() {
    this.showFileModal = !this.showFileModal;
  }

  uploadFile() {
    this.spinner.show();
    if (CommonUtil.isNullOrEmptyOrUndefined(this.selectedFile)) {
      return;
    }
    this.fileService.uploadFile(this.internalAccess, this.selectedFile)
      .subscribe({
        next: (resp: any) => {
          this.load();
          this.notification.fireAndForget({ message: "File Uploaded" }, NotificationType.INFO);
          this.toggleFileModal();
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

  handleFileModal(event: boolean) {
    this.showFileModal = event;
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  convertSize(bytes: number) {
    return CommonUtil.convertSize(bytes);
  }

  openLink(element: any) {
    if (element.acl) {
      this.spinner.show();
      this.fileService.downloadFile(element.rootid)
        .subscribe({
          next: async (resp: any) => {
            let contentDisposition = resp.headers.get('content-disposition');
            let filename = contentDisposition.split(';')[1].split('filename')[1].split('=')[1].trim();
            saveAs(resp.body, filename);
          },
          error: (err: any) => {
            this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
          },
          complete: () => {
            this.spinner.hide();
          }
        })
    }
    else {
      window.open(element.mediaurl, "_blank");
    }
  }

  deleteFile(rootId: number) {
    this.spinner.show();
    this.fileService.deleteFile(rootId)
      .subscribe({
        next: (resp: any) => {
          this.load();
        },
        error: (err: any) => {
          this.notification.fireAndWaitError(CommonUtil.generateErrorNotificationFromResponse(err));
        },
        complete: () => {
          this.spinner.hide();
        }
      })
  }

}
