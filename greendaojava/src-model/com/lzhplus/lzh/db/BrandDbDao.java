package com.lzhplus.lzh.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.lzhplus.lzh.db.BrandDb;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BRAND_DB".
*/
public class BrandDbDao extends AbstractDao<BrandDb, Long> {

    public static final String TABLENAME = "BRAND_DB";

    /**
     * Properties of entity BrandDb.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property BrandId = new Property(0, Integer.class, "brandId", false, "BRAND_ID");
        public final static Property Since = new Property(1, Integer.class, "since", false, "SINCE");
        public final static Property CityId = new Property(2, Integer.class, "cityId", false, "CITY_ID");
        public final static Property ProvinceId = new Property(3, Integer.class, "provinceId", false, "PROVINCE_ID");
        public final static Property OperationType = new Property(4, Integer.class, "operationType", false, "OPERATION_TYPE");
        public final static Property BrandName = new Property(5, String.class, "brandName", false, "BRAND_NAME");
        public final static Property BrandLogoImg = new Property(6, String.class, "brandLogoImg", false, "BRAND_LOGO_IMG");
        public final static Property BigLogoImg = new Property(7, String.class, "bigLogoImg", false, "BIG_LOGO_IMG");
        public final static Property BrandTitle = new Property(8, String.class, "brandTitle", false, "BRAND_TITLE");
        public final static Property BrandDesc = new Property(9, String.class, "brandDesc", false, "BRAND_DESC");
        public final static Property Id = new Property(10, long.class, "id", true, "_id");
    };


    public BrandDbDao(DaoConfig config) {
        super(config);
    }
    
    public BrandDbDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BRAND_DB\" (" + //
                "\"BRAND_ID\" INTEGER," + // 0: brandId
                "\"SINCE\" INTEGER," + // 1: since
                "\"CITY_ID\" INTEGER," + // 2: cityId
                "\"PROVINCE_ID\" INTEGER," + // 3: provinceId
                "\"OPERATION_TYPE\" INTEGER," + // 4: operationType
                "\"BRAND_NAME\" TEXT," + // 5: brandName
                "\"BRAND_LOGO_IMG\" TEXT," + // 6: brandLogoImg
                "\"BIG_LOGO_IMG\" TEXT," + // 7: bigLogoImg
                "\"BRAND_TITLE\" TEXT," + // 8: brandTitle
                "\"BRAND_DESC\" TEXT," + // 9: brandDesc
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL );"); // 10: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BRAND_DB\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, BrandDb entity) {
        stmt.clearBindings();
 
        Integer brandId = entity.getBrandId();
        if (brandId != null) {
            stmt.bindLong(1, brandId);
        }
 
        Integer since = entity.getSince();
        if (since != null) {
            stmt.bindLong(2, since);
        }
 
        Integer cityId = entity.getCityId();
        if (cityId != null) {
            stmt.bindLong(3, cityId);
        }
 
        Integer provinceId = entity.getProvinceId();
        if (provinceId != null) {
            stmt.bindLong(4, provinceId);
        }
 
        Integer operationType = entity.getOperationType();
        if (operationType != null) {
            stmt.bindLong(5, operationType);
        }
 
        String brandName = entity.getBrandName();
        if (brandName != null) {
            stmt.bindString(6, brandName);
        }
 
        String brandLogoImg = entity.getBrandLogoImg();
        if (brandLogoImg != null) {
            stmt.bindString(7, brandLogoImg);
        }
 
        String bigLogoImg = entity.getBigLogoImg();
        if (bigLogoImg != null) {
            stmt.bindString(8, bigLogoImg);
        }
 
        String brandTitle = entity.getBrandTitle();
        if (brandTitle != null) {
            stmt.bindString(9, brandTitle);
        }
 
        String brandDesc = entity.getBrandDesc();
        if (brandDesc != null) {
            stmt.bindString(10, brandDesc);
        }
        stmt.bindLong(11, entity.getId());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 10);
    }    

    /** @inheritdoc */
    @Override
    public BrandDb readEntity(Cursor cursor, int offset) {
        BrandDb entity = new BrandDb( //
            cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0), // brandId
            cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1), // since
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // cityId
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // provinceId
            cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4), // operationType
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // brandName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // brandLogoImg
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // bigLogoImg
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // brandTitle
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // brandDesc
            cursor.getLong(offset + 10) // id
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, BrandDb entity, int offset) {
        entity.setBrandId(cursor.isNull(offset + 0) ? null : cursor.getInt(offset + 0));
        entity.setSince(cursor.isNull(offset + 1) ? null : cursor.getInt(offset + 1));
        entity.setCityId(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setProvinceId(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setOperationType(cursor.isNull(offset + 4) ? null : cursor.getInt(offset + 4));
        entity.setBrandName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBrandLogoImg(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setBigLogoImg(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setBrandTitle(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setBrandDesc(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setId(cursor.getLong(offset + 10));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(BrandDb entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(BrandDb entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
