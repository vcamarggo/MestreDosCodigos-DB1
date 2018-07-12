#lang racket
(require srfi/13)

;; imutabilidade em racket todo dado eh imutavel, ja que as funcoes nao alteram seu valor original, apenas retornam um valor novo,
;; funções puras em racket toda funcao nao-estocastica eh pura, visto que dado uma entrada, sempre retorna a mesma saida.
;; Vale notar que esta aplicacao nao contem nenhuma funcao estocastica, logo, todas as funcoes sao puras.
;; acumuladores utilizados em diagonal-segura?,
;; funções aninhadas utilizados em diagonal-segura? e n-rainhas,

;; Lista Numero -> Lógico
;; ---------------------
;; verifica se a insercao em uma linha eh segura
(define (linha-segura? lista nr)
(cond
    [(empty? lista) #t]
    [(equal? (first lista) nr) #f]
    [else
(linha-segura? (rest lista) nr)]))


;; Lista Numero -> Lógico
;; ---------------------
;; verifica se a insercao em uma diagonal eh segura
;; possui a funcao diagonal-segura-body? que faz a
;; verificacao de fato
;; a funcao externa diagonal-segura? eh apenas um wrapper
;; a funcao utiliza um acumulador internamente para simular
;; indice de um iterador
(define (diagonal-segura? lista nr)
  (let ([size (length lista)])
  (define (diagonal-segura-body? lista nr acc)
    (cond
      [(empty? lista) #t]
      [(equal? (abs(- (first lista) nr)) (abs(- acc size))) #f]
      [else
       (diagonal-segura-body? (rest lista) nr (add1 acc))]))
(diagonal-segura-body? lista nr 0)))

;; Lista Numero -> Lógico
;; ---------------------
;; implementa a verificacao geral se pode inserir um elemento no tabuleiro
(define (seguro-inserir? lista nr)
  (and (diagonal-segura? lista nr) (linha-segura? lista nr)))

;; Lista Numero -> Numero
;; ---------------------
;; encontra o index do primeiro que ainda pode aumentar
(define (ultimo-visitado-idx lista tamanho)
  (cond
    [(< (last lista) (- tamanho 1)) (index-of lista (last lista))]
    [else (ultimo-visitado-idx (take lista (-(length lista) 1)) tamanho)]))

;; Lista Numero -> Numero
;; ---------------------
;; encontra o valor do primeiro que ainda pode aumentar
(define (ultimo-visitado-value lista tamanho)
  (cond
    [(< (last lista) (- tamanho 1)) (last lista)]
    [else (ultimo-visitado-value (take lista (-(length lista) 1)) tamanho)]))

;; Numero -> Lista
;; ---------------------
;; resolve o N-rainhas resultando em apenas uma solucao, mesmo havendo mais.
(define (n-rainhas tamanho)
  (define (n-rainhas-body lista nr tamanho)
  (cond
    [(equal?(length lista) tamanho) (map add1 lista)]
    [(seguro-inserir? lista nr) (n-rainhas-body (append lista (list nr)) 0 tamanho)]
    [(< nr (- tamanho 1)) (n-rainhas-body lista (add1 nr) tamanho)]
    [(equal? nr (- tamanho 1)) (n-rainhas-body (take lista (ultimo-visitado-idx lista tamanho)) (add1 (ultimo-visitado-value lista tamanho)) tamanho)])
    )
  (cond
    ((or (>= tamanho 4) (equal? tamanho 0)) (n-rainhas-body '() 0 tamanho))
    (else "numero de rainhas muito pequeno, nao ha solucao")))

;; Input -> Output
;; -----------------
;; Gera as interacoes com o usuario e exibe a solucao do problema
(display "Digite o numero de rainhas desejadas: ")
(display (n-rainhas (string->number (read-line (current-input-port) 'any))))
(display "\nPressione enter para finalizar")
(read-line (current-input-port) 'any)