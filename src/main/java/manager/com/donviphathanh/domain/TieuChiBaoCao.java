package manager.com.donviphathanh.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TieuChiBaoCao.
 */
@Entity
@Table(name = "tieu_chi_bao_cao")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TieuChiBaoCao implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tieu_chi_bao_cao_code", nullable = false)
    private String tieuChiBaoCaoCode;

    @Column(name = "tieu_chi_code")
    private String tieuChiCode;

    @Column(name = "nhom_danh_muc_code")
    private String nhomDanhMucCode;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "create_time")
    private String createTime;

    @Column(name = "update_time")
    private ZonedDateTime updateTime;

    @Column(name = "status")
    private String status;

    @Column(name = "program")
    private String program;

    @ManyToOne
    @JsonIgnoreProperties("tieuchibaocaos")
    private TieuChi tieuchi;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTieuChiBaoCaoCode() {
        return tieuChiBaoCaoCode;
    }

    public TieuChiBaoCao tieuChiBaoCaoCode(String tieuChiBaoCaoCode) {
        this.tieuChiBaoCaoCode = tieuChiBaoCaoCode;
        return this;
    }

    public void setTieuChiBaoCaoCode(String tieuChiBaoCaoCode) {
        this.tieuChiBaoCaoCode = tieuChiBaoCaoCode;
    }

    public String getTieuChiCode() {
        return tieuChiCode;
    }

    public TieuChiBaoCao tieuChiCode(String tieuChiCode) {
        this.tieuChiCode = tieuChiCode;
        return this;
    }

    public void setTieuChiCode(String tieuChiCode) {
        this.tieuChiCode = tieuChiCode;
    }

    public String getNhomDanhMucCode() {
        return nhomDanhMucCode;
    }

    public TieuChiBaoCao nhomDanhMucCode(String nhomDanhMucCode) {
        this.nhomDanhMucCode = nhomDanhMucCode;
        return this;
    }

    public void setNhomDanhMucCode(String nhomDanhMucCode) {
        this.nhomDanhMucCode = nhomDanhMucCode;
    }

    public String getUserName() {
        return userName;
    }

    public TieuChiBaoCao userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public TieuChiBaoCao createTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public TieuChiBaoCao updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public TieuChiBaoCao status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgram() {
        return program;
    }

    public TieuChiBaoCao program(String program) {
        this.program = program;
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public TieuChi getTieuchi() {
        return tieuchi;
    }

    public TieuChiBaoCao tieuchi(TieuChi tieuChi) {
        this.tieuchi = tieuChi;
        return this;
    }

    public void setTieuchi(TieuChi tieuChi) {
        this.tieuchi = tieuChi;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TieuChiBaoCao tieuChiBaoCao = (TieuChiBaoCao) o;
        if (tieuChiBaoCao.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tieuChiBaoCao.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TieuChiBaoCao{" +
            "id=" + getId() +
            ", tieuChiBaoCaoCode='" + getTieuChiBaoCaoCode() + "'" +
            ", tieuChiCode='" + getTieuChiCode() + "'" +
            ", nhomDanhMucCode='" + getNhomDanhMucCode() + "'" +
            ", userName='" + getUserName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", program='" + getProgram() + "'" +
            "}";
    }
}
