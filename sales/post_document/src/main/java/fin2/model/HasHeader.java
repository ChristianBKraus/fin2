package fin2.model;

import lombok.Data;

public class HasHeader<H> {
    H header;
    public H accessHeader() { return header; }
    public void provideHeader(H header) { this.header = header; }
}
