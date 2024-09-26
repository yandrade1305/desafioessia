import { Component, Input } from '@angular/core';

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

@Component({
  selector: 'app-directory',
  templateUrl: './directory.component.html',
  styleUrls: ['./directory.component.css']
})
export class DirectoryComponent {
  @Input() directory: DirectoryItem = { id: 0, name: '', files: [], subdirectories: [] };
}
