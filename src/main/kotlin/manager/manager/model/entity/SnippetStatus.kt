package manager.manager.model.entity

import jakarta.persistence.*
import manager.common.entity.CommonEntity
import manager.manager.model.enums.ComplianceSnippet

@Entity
data class SnippetStatus(
    @Enumerated(EnumType.STRING)
    var status: ComplianceSnippet,
    @OneToMany(mappedBy = "status_id")
    val snippet: List<Snippet> = ArrayList(),
) : CommonEntity() {
    constructor() : this(ComplianceSnippet.PENDING)
}