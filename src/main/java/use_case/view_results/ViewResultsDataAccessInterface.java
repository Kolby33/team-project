package use_case.view_results;

import entity.Attempt;

import java.util.Optional;

public interface ViewResultsDataAccessInterface {
    Optional<Attempt> findById(String attemptId);
}