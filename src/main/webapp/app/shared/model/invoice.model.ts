export interface IInvoice {
  id?: number;
  attachmentUrl?: string;
  pdfContentType?: string;
  pdf?: any;
  companyId?: string;
}

export class Invoice implements IInvoice {
  constructor(
    public id?: number,
    public attachmentUrl?: string,
    public pdfContentType?: string,
    public pdf?: any,
    public companyId?: string
  ) {}
}
