package tn.esprit.autoloc.service;

public interface IGenericService<T, ID>
        extends ICheckService<T>,
        IReadService<T, ID>,
        IWriteService<T, ID> {
}