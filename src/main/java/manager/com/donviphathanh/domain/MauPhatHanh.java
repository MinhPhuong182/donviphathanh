package manager.com.donviphathanh.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A MauPhatHanh.
 */
@Entity
@Table(name = "mau_phat_hanh")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MauPhatHanh implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mau_phat_hanh_code", nullable = false)
    private String mauPhatHanhCode;

    @Column(name = "name")
    private String name;

    @Column(name = "tieu_chi_code")
    private String tieuChiCode;

    @Column(name = "nhom_phan_loai_code")
    private String nhomPhanLoaiCode;

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

    @OneToMany(mappedBy = "mauphathanh")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MauPhatHanhTieuChi> mauphathanhtieuchis = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMauPhatHanhCode() {
        return mauPhatHanhCode;
    }

    public MauPhatHanh mauPhatHanhCode(String mauPhatHanhCode) {
        this.mauPhatHanhCode = mauPhatHanhCode;
        return this;
    }

    public void setMauPhatHanhCode(String mauPhatHanhCode) {
        this.mauPhatHanhCode = mauPhatHanhCode;
    }

    public String getName() {
        return name;
    }

    public MauPhatHanh name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTieuChiCode() {
        return tieuChiCode;
    }

    public MauPhatHanh tieuChiCode(String tieuChiCode) {
        this.tieuChiCode = tieuChiCode;
        return this;
    }

    public void setTieuChiCode(String tieuChiCode) {
        this.tieuChiCode = tieuChiCode;
    }

    public String getNhomPhanLoaiCode() {
        return nhomPhanLoaiCode;
    }

    public MauPhatHanh nhomPhanLoaiCode(String nhomPhanLoaiCode) {
        this.nhomPhanLoaiCode = nhomPhanLoaiCode;
        return this;
    }

    public void setNhomPhanLoaiCode(String nhomPhanLoaiCode) {
        this.nhomPhanLoaiCode = nhomPhanLoaiCode;
    }

    public String getUserName() {
        return userName;
    }

    public MauPhatHanh userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public MauPhatHanh createTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public MauPhatHanh updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getStatus() {
        return status;
    }

    public MauPhatHanh status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProgram() {
        return program;
    }

    public MauPhatHanh program(String program) {
        this.program = program;
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public Set<MauPhatHanhTieuChi> getMauphathanhtieuchis() {
        return mauphathanhtieuchis;
    }

    public MauPhatHanh mauphathanhtieuchis(Set<MauPhatHanhTieuChi> mauPhatHanhTieuChis) {
        this.mauphathanhtieuchis = mauPhatHanhTieuChis;
        return this;
    }

    public MauPhatHanh addMauphathanhtieuchi(MauPhatHanhTieuChi mauPhatHanhTieuChi) {
        this.mauphathanhtieuchis.add(mauPhatHanhTieuChi);
        mauPhatHanhTieuChi.setMauphathanh(this);
        return this;
    }

    public MauPhatHanh removeMauphathanhtieuchi(MauPhatHanhTieuChi mauPhatHanhTieuChi) {
        this.mauphathanhtieuchis.remove(mauPhatHanhTieuChi);
        mauPhatHanhTieuChi.setMauphathanh(null);
        return this;
    }

    public void setMauphathanhtieuchis(Set<MauPhatHanhTieuChi> mauPhatHanhTieuChis) {
        this.mauphathanhtieuchis = mauPhatHanhTieuChis;
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
        MauPhatHanh mauPhatHanh = (MauPhatHanh) o;
        if (mauPhatHanh.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), mauPhatHanh.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MauPhatHanh{" +
            "id=" + getId() +
            ", mauPhatHanhCode='" + getMauPhatHanhCode() + "'" +
            ", name='" + getName() + "'" +
            ", tieuChiCode='" + getTieuChiCode() + "'" +
            ", nhomPhanLoaiCode='" + getNhomPhanLoaiCode() + "'" +
            ", userName='" + getUserName() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", program='" + getProgram() + "'" +
            "}";
    }
}
