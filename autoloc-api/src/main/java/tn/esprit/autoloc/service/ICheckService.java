package tn.esprit.autoloc.service;

public interface ICheckService<T> {
    boolean check(T entity, boolean isNew);
}