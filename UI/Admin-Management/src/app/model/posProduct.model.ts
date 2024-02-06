export class PosProduct {

    public barCode!: string;
    public productName!: string;
    public mrp!: number;
    public quantity!: number;
    public discount!: number;
    public price!: number;
  
    constructor() {
      this.mrp = 0;
      this.quantity = 1;
      this.discount = 0;
    }
  
  }