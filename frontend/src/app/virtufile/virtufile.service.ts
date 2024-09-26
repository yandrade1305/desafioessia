import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

interface FileItem {
  id: number;
  name: string;
}

interface DirectoryItem {
  id: number;
  name: string;
  files: FileItem[];
  subdirectories: DirectoryItem[];
}

@Injectable({
  providedIn: 'root'
})
export class VirtufileService {
  private apiUrl = 'http://localhost:8080/api/directory/get';

  constructor(private http: HttpClient) {}

  getFileSystem(): Observable<DirectoryItem[]> {
    return this.http.get<DirectoryItem[]>(this.apiUrl);
  }
}
