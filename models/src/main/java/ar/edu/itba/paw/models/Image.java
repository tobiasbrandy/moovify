package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_image_id_seq")
    @SequenceGenerator(sequenceName = "images_image_id_seq", name = "images_image_id_seq", allocationSize = 1)
    private Long id;

    @Lob
    @Column(name = "image", nullable = false, columnDefinition = "BLOB") //TODO puede haber error usando BLOB aca
    @Basic(fetch = FetchType.LAZY, optional = false)
    private byte[] data;

    @Column(name = "security_tag", nullable = false)
    @Basic(optional = false)
    private String tag;

    public Long getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public String getTag() {
        return tag;
    }

    protected Image() {
        // Hibernate
    }

    public Image(byte[] data, String tag) {
        this.data = data;
        this.tag = tag;
    }

    public Image(Long id, byte[] data, String tag) {
        this(data, tag);
        this.id = id;
    }
}