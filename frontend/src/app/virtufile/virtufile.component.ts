import { Component, OnInit } from '@angular/core';
import { VirtufileService } from './virtufile.service';

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
  selector: 'app-virtufile',
  templateUrl: './virtufile.component.html',
  styleUrls: ['./virtufile.component.css']
})
export class VirtufileComponent implements OnInit {
  virtufile: DirectoryItem[] = [];

  constructor(private virtufileService: VirtufileService) {}

  ngOnInit(): void {
    this.virtufileService.getFileSystem().subscribe((data: DirectoryItem[]) => {
      this.virtufile = data;
    });
  }
}
