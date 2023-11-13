import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class FileUtil {

  static isValidPictureFile(name: String) {
    var ext = name.substring(name.lastIndexOf('.') + 1);
    if (ext.toLowerCase() == 'png' || ext.toLowerCase() == 'jpeg' || ext.toLowerCase() == 'jpg') {
        return true;
    }
    else {
        return false;
    }
  }

}
